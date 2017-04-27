package cz.muni.fi.pv260.productfilter;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.CatchException.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 *
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class AtLeastNOfFilterTest {
    
    @Test
    public void testConstructorThrowsFilterNeverSucceedsException() throws Exception{
        verifyException(() -> new AtLeastNOfFilter<>(1));
        
        assertThat((Exception) caughtException()).isInstanceOf(FilterNeverSucceeds.class);
    }
    
    @Test
    public void testConstructorThrowsIllegalArgumentException() throws Exception{
        verifyException(() -> new AtLeastNOfFilter<>(0));
        
        assertThat((Exception) caughtException()).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    // Manual test doubles
    public void testFilterPassesIfEnoughChildrenPass() {
        Object itemToBeFiltered = new Object();
        
        Filter<Object> alwaysPassingFilter1 = (Object item) -> true;
        Filter<Object> alwaysPassingFilter2 = (Object item) -> true;
        Filter<Object> neverPassingFilter =(Object item) -> false;
        
        final int numberOfFiltersToPassAtLeast = 2;
        
        AtLeastNOfFilter<Object> testedFilter = new AtLeastNOfFilter<>(numberOfFiltersToPassAtLeast, 
            alwaysPassingFilter1, neverPassingFilter, alwaysPassingFilter2);
        
        boolean testedValue = testedFilter.passes(itemToBeFiltered);
        boolean expectedValue = true;
        
        assertThat(testedValue).isEqualTo(expectedValue);
    }
    
    @Test
    public void testFilterFailsIfNotEnoughChildrenPass() {
        Object item = mock(Object.class);
        
        Filter<Object> alwaysPassingFilter = mock(Filter.class);
        Filter<Object> neverPassingFilter1 = mock(Filter.class);
        Filter<Object> neverPassingFilter2 = mock(Filter.class);
        
        when(alwaysPassingFilter.passes(item)).thenReturn(true);
        when(neverPassingFilter1.passes(item)).thenReturn(false);
        when(neverPassingFilter2.passes(item)).thenReturn(false);
        
        final int numberOfFiltersToPassAtLeast = 2;
        
        AtLeastNOfFilter<Object> testedFilter = new AtLeastNOfFilter<>(numberOfFiltersToPassAtLeast, 
            alwaysPassingFilter, neverPassingFilter2, neverPassingFilter1);
        
        boolean testedValue = testedFilter.passes(item);
        boolean expectedValue = false;
        
        assertThat(testedValue).isEqualTo(expectedValue);
    }
}
