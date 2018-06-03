package lwj.app.utils.validation.textFilter;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
  * 
  * @author LF
  *
  */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
// Validator 설정
@Constraint(validatedBy = TextFilterValidator.class)
@Documented
public @interface TextFilter {
	
	// Default message
    String message() default "Text filter";
 
    // Default group is null
    Class<?>[] groups() default { };
 
    Class<? extends Payload>[] payload() default { };
 
    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
    	TextFilter[] value();
    }
    
}
