package lwj.app.utils.validation.textFilter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
  * TextFilter 불법테스트 필터링
  * @author LF
  *
  */
public class TextFilterValidator implements ConstraintValidator<TextFilter, String> {

	private String[] TextFilterWords = {"admin"};
	
	@Override
	public void initialize(TextFilter constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(StringUtils.isEmpty(value)) {
            return true;
        }
		
		for(String word : TextFilterWords) {
            if(value.contains(word)) {
                return false;
            }
        }
		
		return true;
	}

}