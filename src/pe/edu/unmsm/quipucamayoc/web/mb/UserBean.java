package pe.edu.unmsm.quipucamayoc.web.mb;

import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
public class UserBean {
private String name;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

}
