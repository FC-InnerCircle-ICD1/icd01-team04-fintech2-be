package incerpay.paygate.domain.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import incerpay.paygate.infrastructure.external.dto.CardApiApproveView;
import incerpay.paygate.presentation.dto.in.CardApiApproveCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentApiLogService {

    private final PaymentApiLogRepository paymentApiLogRepository;
    private final ObjectMapper objectMapper;

    // TODO CardApiApproveCommand -> PaymentApproveCommand
    public PaymentApiLog saveRequestLog(UUID paymentId, CardApiApproveCommand request) {
        log.info("Attempting to save request log for paymentId: {}", paymentId);

        try {
            PaymentApiLog requestLog = PaymentApiLog.ofRequest(
                    paymentId,
                    serializeRequest(request)
            );

            PaymentApiLog saved = paymentApiLogRepository.save(requestLog);
            log.info("Request log saved with id: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            log.error("Failed to save request log to database. PaymentId: {}, Error: {}",
                    paymentId, e.getMessage(), e);
            throw e;
        }
    }

    // TODO CardApiApproveView -> PaymentApproveView
    public PaymentApiLog saveResponseLog(UUID paymentId, CardApiApproveView response) {
        PaymentApiLog responseLog = PaymentApiLog.ofResponse(
                paymentId,
                serializeRequest(response)
        );
        return paymentApiLogRepository.save(responseLog);
    }

    public PaymentApiLog saveErrorLog(UUID paymentId, Exception e) {
        PaymentApiLog errorLog = PaymentApiLog.ofError(
                paymentId,
                e.getMessage()
        );
        return paymentApiLogRepository.save(errorLog);
    }


    private String serializeRequest(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
