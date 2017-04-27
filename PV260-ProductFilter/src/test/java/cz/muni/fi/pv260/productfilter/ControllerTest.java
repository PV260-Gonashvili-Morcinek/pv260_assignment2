package cz.muni.fi.pv260.productfilter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class ControllerTest {
    
    
    @Test
    public void testSendsCorrectSelectedProductsToOutput() {
        Product productToPass = new Product(0, "The Fellowship of the Ring", Color.GREEN, BigDecimal.TEN);
        Product productToNotPass = new Product(1, "Balrog", Color.RED, BigDecimal.ONE);
        
        Collection<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(productToPass);
        
        Input input = () -> {
            Collection<Product> products = new ArrayList<>();
            products.add(productToPass);
            products.add(productToNotPass);
            
            return products;
        };
        
        Output spyOutput = spy(new Output(){
            @Override
            public void postSelectedProducts(Collection<Product> products) {}
        });
        
        Logger logger = mock(Logger.class);
        
        Filter<Product> filter = (item) -> {
            return item.equals(productToPass);
        };
        
        Controller controller = new Controller(input, spyOutput, logger);
        
        controller.select(filter);
        
        verify(spyOutput).postSelectedProducts(expectedProducts);
    }
    
    @Test
    public void testLogsCorrectMessageOnSuccess() {
        Product productToPass = new Product(0, "The Fellowship of the Ring", Color.GREEN, BigDecimal.TEN);
        Product productToNotPass = new Product(1, "Balrog", Color.RED, BigDecimal.ONE);
        
        String expectedMessage = "Successfully selected 1 out of 2 available products.";
        
        Input input = () -> {
            List<Product> products = new ArrayList<>();
            products.add(productToPass);
            products.add(productToNotPass);
            
            return products;
        };
        
        Output output = (products) -> {};
        
        Logger spyLogger = spy(new Logger(){
            @Override
            public void setLevel(String level) {}

            @Override
            public void log(String tag, String message) {
            }
        });
        
        Filter<Product> filter = (item) -> {
            return item.equals(productToPass);
        };
        
        Controller controller = new Controller(input, output, spyLogger);
        
        controller.select(filter);
        
        verify(spyLogger).log(Controller.TAG_CONTROLLER, expectedMessage);
    }
    
    @Test
    public void testLogsObtainFailedException() {   
        ObtainFailedException exceptionToBeThrown = new ObtainFailedException();
        
        Input input = () -> {
            throw exceptionToBeThrown;
        };
        
        String expectedMessage = "Filter procedure failed with exception: " + 
                exceptionToBeThrown;
        
        Output output = mock(Output.class);
        
        Logger spyLogger = spy(new Logger(){
            @Override
            public void setLevel(String level) {}

            @Override
            public void log(String tag, String message) {}
        });
        
        Filter<Product> filter = mock(Filter.class);
        
        Controller controller = new Controller(input, output, spyLogger);
        controller.select(filter);
        
        verify(spyLogger).log(Controller.TAG_CONTROLLER, expectedMessage);
    }
    
    @Test
    public void testPassesNothingAfterObtainFailedException() {     
        Input input = () -> {
            throw new ObtainFailedException();
        };
        
        Output spyOutput = spy(new Output(){
            @Override
            public void postSelectedProducts(Collection<Product> products) {}  
        });
        
        Logger logger = mock(Logger.class);
        
        Filter<Product> filter = mock(Filter.class);
        
        Controller controller = new Controller(input, spyOutput, logger);
        controller.select(filter);
        
        verify(spyOutput, Mockito.times(0)).postSelectedProducts(Mockito.any());
    }
}
