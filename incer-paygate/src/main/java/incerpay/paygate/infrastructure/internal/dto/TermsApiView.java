package incerpay.paygate.infrastructure.internal.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class TermsApiView {

    private List<String> terms;

}
