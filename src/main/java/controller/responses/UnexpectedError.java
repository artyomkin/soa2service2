package controller.responses;

import domain.XMLResponse;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UnexpectedError")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class UnexpectedError implements XMLResponse {
    private Integer code;
    private String msg;
    public UnexpectedError(){}
    public UnexpectedError(String msg){
        this.msg = msg;
        this.code = 400;
    }

    @Override
    public Integer getStatus() {
        return this.code;
    }
}
