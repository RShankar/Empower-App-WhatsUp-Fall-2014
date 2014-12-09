package edu.fau.whatsup.UnitTests;

import android.test.InstrumentationTestCase;

import edu.fau.whatsup.Controls.Accordion.AccordionList;
import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Entities.EventCategory;

import java.util.ArrayList;
import java.util.Date;

public class AccordionListTests extends InstrumentationTestCase {

    private Event getEvent(int id){
        Event retVal = new Event();

        retVal.set_startTime(new Date());
        retVal.set_endTime(new Date());
        retVal.set_website("random website " + id);
        retVal.set_category(EventCategory.getCategories()[id % 4]);

        return retVal;
    }

    public void testCreateAccordionListFromCollection(){
        ArrayList<Event> allEvents = new ArrayList<Event>();
        int maxEventsToCreate = 50;
        for (int i=0; i<maxEventsToCreate; i++){
            allEvents.add(getEvent(i));
        }

        AccordionList<Event> testEventList = new AccordionList<Event>();
        testEventList.CreateFromListAndSectionSpecification(allEvents, "get_category_name");

        assertTrue(testEventList.GetSection(EventCategory.Sports.getName()) != null);
        assertTrue(testEventList.GetSection(EventCategory.Fitness.getName()) != null);
        assertTrue(testEventList.GetSection(EventCategory.Scholastic.getName()) != null);
        assertTrue(testEventList.GetSection(EventCategory.Volunteering.getName()) != null);

        assertEquals(testEventList.GetSection(EventCategory.Sports.getName()).GetItems().size(), 13);
        assertEquals(testEventList.GetSection(EventCategory.Fitness.getName()).GetItems().size(), 13);
        assertEquals(testEventList.GetSection(EventCategory.Scholastic.getName()).GetItems().size(), 12);
        assertEquals(testEventList.GetSection(EventCategory.Volunteering.getName()).GetItems().size(), 12);
    }

    public void testCreateAccordionListFromStringsManuallyEntered(){
        AccordionList<String> testList = new AccordionList<String>();

        String test1Section = "test1";
        String test2Section = "test2";

        testList.AddSection(test1Section);
        testList.AddSection(test2Section);

        testList.GetSection(test1Section).AddItem("test1Child1");
        testList.GetSection(test1Section).AddItem("test1Child2");

        testList.GetSection(test2Section).AddItem("test2Child1");
        testList.GetSection(test2Section).AddItem("test2Child2");
        testList.GetSection(test2Section).AddItem("test2Child3");

        assertTrue(testList.GetSection(test1Section).GetItems().size() == 2);
        assertTrue(testList.GetSection(test2Section).GetItems().size() == 3);
    }

}
