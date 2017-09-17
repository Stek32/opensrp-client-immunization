package org.smartregister.immunization.db;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.smartregister.immunization.BaseUnitTest;
import org.smartregister.immunization.domain.Vaccine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

/**
 * Created by onaio on 30/08/2017.
 */

public class VaccineRepoTest extends BaseUnitTest {

    @Test
    public void assertDefaultConstructorsCreateNonNullObjectOnInstantiation() {

    }

    @Test
    public void assertGetVaccinesWillReturnAccordingToCategoryWoman() {
        ArrayList<VaccineRepo.Vaccine> vaccines = new ArrayList<VaccineRepo.Vaccine>();
        vaccines.add(VaccineRepo.Vaccine.tt1);
        vaccines.add(VaccineRepo.Vaccine.tt2);
        vaccines.add(VaccineRepo.Vaccine.tt3);
        vaccines.add(VaccineRepo.Vaccine.tt4);
        vaccines.add(VaccineRepo.Vaccine.tt5);
        assertEquals(vaccines,VaccineRepo.getVaccines("woman"));
        assertEquals(vaccines.get(0),VaccineRepo.getVaccine("TT 1","woman"));
        assertEquals(vaccines.get(1),VaccineRepo.getVaccine("TT 2","woman"));
        assertEquals(vaccines.get(2),VaccineRepo.getVaccine("TT 3","woman"));
        assertEquals(vaccines.get(3),VaccineRepo.getVaccine("TT 4","woman"));
        assertEquals(vaccines.get(4),VaccineRepo.getVaccine("TT 5","woman"));
    }

    @Test
    public void assertGetVaccinesWillReturnAccordingToCategoryChild() {

        ArrayList<VaccineRepo.Vaccine> vaccines = new ArrayList<VaccineRepo.Vaccine>();
        vaccines.add(VaccineRepo.Vaccine.bcg);
        vaccines.add(VaccineRepo.Vaccine.opv0);

        vaccines.add(VaccineRepo.Vaccine.opv1);
        vaccines.add(VaccineRepo.Vaccine.penta1);
        vaccines.add(VaccineRepo.Vaccine.pcv1);
        vaccines.add(VaccineRepo.Vaccine.rota1);

        vaccines.add(VaccineRepo.Vaccine.opv2);
        vaccines.add(VaccineRepo.Vaccine.penta2);
        vaccines.add(VaccineRepo.Vaccine.pcv2);
        vaccines.add(VaccineRepo.Vaccine.rota2);

        vaccines.add(VaccineRepo.Vaccine.opv3);
        vaccines.add(VaccineRepo.Vaccine.penta3);
        vaccines.add(VaccineRepo.Vaccine.pcv3);

        vaccines.add(VaccineRepo.Vaccine.measles1);
        vaccines.add(VaccineRepo.Vaccine.mr1);
        vaccines.add(VaccineRepo.Vaccine.opv4);


        vaccines.add(VaccineRepo.Vaccine.measles2);
        vaccines.add(VaccineRepo.Vaccine.mr2);


        vaccines.add(VaccineRepo.Vaccine.bcg2);
        vaccines.add(VaccineRepo.Vaccine.ipv);



        assertEquals(vaccines,VaccineRepo.getVaccines("child"));
    }



    @Test
    public void assertGetNextVaccinesWillReturnNotNull() {
        assertNotNull(VaccineRepo.nextVaccines(""));
    }

    @Test
    public void assertGetDisplayWillReturnNotNull() {
        VaccineRepo.Vaccine vaccine = VaccineRepo.Vaccine.opv0;
        assertNotNull(vaccine.display());
    }

    @Test
    public void assertGetCategoryWillReturnNotNull() {
        VaccineRepo.Vaccine vaccine = VaccineRepo.Vaccine.opv0;
        assertNotNull(vaccine.category());
    }

    @Test
    public void assertGetPrerequisiteWillReturnNotNull() {
        VaccineRepo.Vaccine vaccine = VaccineRepo.Vaccine.tt4;
        assertNotNull(vaccine.prerequisite());
    }

    @Test
    public void assertGetPrerequisiteGapDaysWillReturnNotNull() {
        VaccineRepo.Vaccine vaccine = VaccineRepo.Vaccine.rota2;
        assertNotNull(vaccine.prerequisiteGapDays());
    }

    @Test
    public void assertGetExpiryDaysWillReturnNotNull() {
        VaccineRepo.Vaccine vaccine = VaccineRepo.Vaccine.pcv1;
        assertNotNull(vaccine.expiryDays());
    }

    @Test
    public void assertGetmilestoneGapDaysWillReturnNotNull() {
        VaccineRepo.Vaccine vaccine = VaccineRepo.Vaccine.opv0;
        assertNotNull(vaccine.milestoneGapDays());
    }



}
