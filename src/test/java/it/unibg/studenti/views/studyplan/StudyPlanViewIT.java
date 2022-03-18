package it.unibg.studenti.views.studyplan;

import it.unibg.studenti.AbstractIT;
import org.junit.Assert;
import org.junit.Test;

public class StudyPlanViewIT extends AbstractIT {

    @Test
    public void fillGrid() {
        getDriver().get("http://localhost:8080/studyplans");
        StudyPlanViewElement view = $(StudyPlanViewElement.class).first();
        view.getSelectYear().doubleClick();
        view.getSelectDegree().doubleClick();
        view.getSelectYearAcc().doubleClick();
        int rowCount = view.getGrid().getRowCount();
        Assert.assertTrue("grid not empty.", rowCount > 0);
    }
}