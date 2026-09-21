package cafe.project.YatiWinLatt.service;



import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WasteReasonService {
    public List<Map<String, String>> getAllWasteReasons() {
        List<Map<String, String>> reasons = new ArrayList<>();
        
        Map<String, String> r1 = new HashMap<>();
        r1.put("waste_reason_id", "WR-001");
        r1.put("reason_name", "Expired Product");
        reasons.add(r1);

        Map<String, String> r2 = new HashMap<>();
        r2.put("waste_reason_id", "WR-002");
        r2.put("reason_name", "Spilled / Damaged");
        reasons.add(r2);

        return reasons;
    }
}


