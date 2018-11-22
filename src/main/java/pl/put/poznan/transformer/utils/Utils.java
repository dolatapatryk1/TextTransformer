package pl.put.poznan.transformer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static pl.put.poznan.transformer.app.TextTransformerApplication.userTransforms;
import pl.put.poznan.transformer.logic.OptionModel;
import pl.put.poznan.transformer.logic.UserTransformModel;

public class Utils {
    
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static boolean checkIfNameIsTaken(String name) {
        OptionModel[] options = loadOptions();
        
        for(OptionModel option : options)
            if(option.getName().equals(name))
                return true;
        
        for(UserTransformModel transform : userTransforms)
            if(transform.getName().equals(name))
                return true;
        
        return false;
    }
    
    public static OptionModel[] loadOptions() {
        Gson gson = new Gson();
        JsonReader jsonReader = null;
        
        try {
            jsonReader = new JsonReader(new InputStreamReader(new FileInputStream("options.json"), "UTF-8"));
            logger.debug("Wczytano opcje z jsona");
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        OptionModel[] options = gson.fromJson(jsonReader, OptionModel[].class);
        
        return options;
    }
    
    public static <T> T objectFromJson(String body, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        T object = null;
        
        try {
            object = mapper.readValue(body, type);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return object;
    }
    
}
