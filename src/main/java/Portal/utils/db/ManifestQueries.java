package Portal.utils.db;

import Portal.utils.logs.LogsManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ManifestQueries — Database query layer for the 'manifests' table.
 *
 * Column reference (from actual DB schema):
 *   id, status_id, manifest_submission_date, manifest_type_id,
 *   manifest_number, user_id, facility_created_by, apa_vessel_visit_id,
 *   voyage_number, estimated_arrival_date, actual_arrival_date,
 *   transport_mean_arrival_timestamp, departure_date, actual_departure_timestamp,
 *   berth_no, shipping_agency_name, has_passengers, passenger_count,
 *   discharge_date, notes, has_secondary_agents, has_empty_containers,
 *   captain_name, deleted_at, created_at, updated_at, facility_created_for,
 *   travel_permission_document, arrival_port, departure_port,
 *   transport_means_type, vessel_country, main_warehouse,
 *   customs_department_code, manifest_isn, DDBIdentification, late_submission
 *
 * Usage in test:
 *   ManifestQueries manifestDB = new ManifestQueries();
 *   manifestDB.deleteManifestByNumber("600/2026/000131");
 */
public class ManifestQueries {

    private final Connection connection;

    public ManifestQueries() {
        this.connection = DBManager.getInstance().getConnection();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // READ Queries
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Checks if a manifest exists in the DB by its manifest_number.
     * @param manifestNumber  e.g. "600/2026/000131"
     * @return true if a row is found (even soft-deleted)
     */
    public boolean doesManifestExist(String manifestNumber) {
        String sql = "SELECT COUNT(*) FROM manifests WHERE manifest_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, manifestNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LogsManager.error("❌ doesManifestExist failed for:", manifestNumber, e.getMessage());
        }
        return false;
    }

    /**
     * Returns the status_id of a manifest.
     * status_id maps to a lookup table — check your DB for the meaning of each value.
     * @param manifestNumber  e.g. "600/2026/000131"
     * @return status_id as int, or -1 if not found
     */
    public int getManifestStatusId(String manifestNumber) {
        String sql = "SELECT status_id FROM manifests WHERE manifest_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, manifestNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int statusId = rs.getInt("status_id");
                LogsManager.info("📄 status_id for manifest", manifestNumber + ":", String.valueOf(statusId));
                return statusId;
            }
        } catch (SQLException e) {
            LogsManager.error("❌ getManifestStatusId failed for:", manifestNumber, e.getMessage());
        }
        return -1;
    }

    /**
     * Returns the DB id of a manifest by its manifest_number.
     * Useful when you need the numeric PK for other operations.
     * @param manifestNumber  e.g. "600/2026/000131"
     * @return id or -1 if not found
     */
    public int getManifestId(String manifestNumber) {
        String sql = "SELECT id FROM manifests WHERE manifest_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, manifestNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LogsManager.error("❌ getManifestId failed for:", manifestNumber, e.getMessage());
        }
        return -1;
    }

    /**
     * Returns manifest_number values that were created (not yet soft-deleted).
     * "Created" = rows where deleted_at IS NULL.
     * @return list of manifest_number strings
     */
    public List<String> getAllActiveManifestNumbers() {
        List<String> numbers = new ArrayList<>();
        String sql = "SELECT manifest_number FROM manifests WHERE deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                numbers.add(rs.getString("manifest_number"));
            }
            LogsManager.info("📋 Active (not deleted) manifests found:", String.valueOf(numbers.size()));
        } catch (SQLException e) {
            LogsManager.error("❌ getAllActiveManifestNumbers failed:", e.getMessage());
        }
        return numbers;
    }

    /**
     * Returns all manifests with a specific status_id (e.g. status_id = 1 for CREATED).
     * @param statusId  the numeric status to filter by
     * @return list of manifest_number strings
     */
    public List<String> getManifestsByStatusId(int statusId) {
        List<String> numbers = new ArrayList<>();
        String sql = "SELECT manifest_number FROM manifests WHERE status_id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, statusId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                numbers.add(rs.getString("manifest_number"));
            }
            LogsManager.info("📋 Manifests with status_id=" + statusId + ":", String.valueOf(numbers.size()));
        } catch (SQLException e) {
            LogsManager.error("❌ getManifestsByStatusId failed:", e.getMessage());
        }
        return numbers;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DELETE Queries
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Hard-deletes a manifest row by manifest_number.
     * Use in @AfterMethod to clean up test data after manifest creation tests.
     *
     * @param manifestNumber  e.g. "600/2026/000131"
     * @return true if deleted successfully
     */
    public boolean deleteManifestByNumber(String manifestNumber) {
        String sql = "DELETE FROM manifests WHERE manifest_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, manifestNumber);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                LogsManager.info("🗑️  Manifest hard-deleted:", manifestNumber);
                return true;
            } else {
                LogsManager.info("⚠️  No manifest found to delete:", manifestNumber);
            }
        } catch (SQLException e) {
            LogsManager.error("❌ deleteManifestByNumber failed for:", manifestNumber, e.getMessage());
        }
        return false;
    }

    /**
     * Hard-deletes a manifest row by its numeric PK (id column).
     * @param id  the manifest's database id
     * @return true if deleted successfully
     */
    public boolean deleteManifestById(int id) {
        String sql = "DELETE FROM manifests WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                LogsManager.info("🗑️  Manifest hard-deleted by id:", String.valueOf(id));
                return true;
            }
        } catch (SQLException e) {
            LogsManager.error("❌ deleteManifestById failed for id:", String.valueOf(id), e.getMessage());
        }
        return false;
    }

    /**
     * Soft-deletes a manifest by setting deleted_at = NOW().
     * Use this if the system uses soft-delete (the deleted_at column exists).
     *
     * @param manifestNumber  e.g. "600/2026/000131"
     * @return true if soft-deleted successfully
     */
    public boolean softDeleteManifestByNumber(String manifestNumber) {
        String sql = "UPDATE manifests SET deleted_at = NOW() WHERE manifest_number = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, manifestNumber);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                LogsManager.info("🗑️  Manifest soft-deleted (deleted_at set):", manifestNumber);
                return true;
            } else {
                LogsManager.info("⚠️  Manifest not found or already soft-deleted:", manifestNumber);
            }
        } catch (SQLException e) {
            LogsManager.error("❌ softDeleteManifestByNumber failed for:", manifestNumber, e.getMessage());
        }
        return false;
    }

    /**
     * Hard-deletes ALL manifests where deleted_at IS NULL (active records).
     * ⚠️ Use carefully — only for full test-suite cleanup.
     * @return number of rows deleted
     */
    public int deleteAllActiveManifests() {
        String sql = "DELETE FROM manifests WHERE deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int rows = stmt.executeUpdate();
            LogsManager.info("🗑️  All active manifests deleted. Rows affected:", String.valueOf(rows));
            return rows;
        } catch (SQLException e) {
            LogsManager.error("❌ deleteAllActiveManifests failed:", e.getMessage());
        }
        return 0;
    }
}
