package portal.Tests;

import Portal.utils.db.DBManager;
import Portal.utils.db.ManifestQueries;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * DeleteManifestTest — DB-only test class (no browser / no Selenium).
 *
 * Group: "manifest-delete"
 * Depends on group "manifest" (i.e., ManifestTest must run first to produce a manifest number).
 *
 * Run order in the suite:
 *   authentication → manifest → manifest-delete
 */
@Epic("Customs Project")
@Feature("Customs Services")
@Story("Delete Manifest")
@Severity(SeverityLevel.NORMAL)
@Owner("Elnems")
public class DeleteManifestTest {

    private ManifestQueries manifestDB;

    // ─────────────────────────────────────────────────────────────────────────

    @Description("Delete the manifest that was created in ManifestTest via direct DB query")
    @Test(groups = "manifest-delete", dependsOnGroups = "manifest")
    public void deleteCreatedManifestTC() {

        String manifestNumber = ManifestTest.lastCreatedManifestNumber;

        // Guard: لو الـ manifest number مش موجود (ManifestTest اتفشل مثلاً)
        Assert.assertNotNull(manifestNumber,
                "❌ Manifest number is null — ManifestTest may have failed or didn't run first.");
        Assert.assertFalse(manifestNumber.isBlank(),
                "❌ Manifest number is blank — cannot delete.");

        // تحقق إن الـ manifest موجود في الـ DB قبل الحذف
        boolean exists = manifestDB.doesManifestExist(manifestNumber);
        Assert.assertTrue(exists,
                "❌ Manifest [" + manifestNumber + "] was not found in DB before delete.");

        // حذف الـ manifest
        boolean deleted = manifestDB.deleteManifestByNumber(manifestNumber);
        Assert.assertTrue(deleted,
                "❌ Failed to delete manifest [" + manifestNumber + "] from DB.");

        // تحقق إنه اتحذف فعلاً
        boolean stillExists = manifestDB.doesManifestExist(manifestNumber);
        Assert.assertFalse(stillExists,
                "❌ Manifest [" + manifestNumber + "] still exists in DB after delete!");
    }


    // ─── Configurations ──────────────────────────────────────────────────────

    @BeforeClass(alwaysRun = true)
    public void preCondition() {
        manifestDB = new ManifestQueries();
    }

    @AfterClass(alwaysRun = true)
    public void closeDBConnection() {
        DBManager.getInstance().closeConnection();
    }
}
