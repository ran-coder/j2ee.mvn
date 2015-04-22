package utils.annotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-10-24 下午4:36:56
 *   
 */
//加载在VM中，在运行时进行映射
@Retention(RetentionPolicy.RUNTIME)
//限定此annotation只能标示方法
@Target({METHOD,ANNOTATION_TYPE,TYPE})
public @interface Untest {

}
