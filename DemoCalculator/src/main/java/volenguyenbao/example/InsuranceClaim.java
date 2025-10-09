package volenguyenbao.example;

public class InsuranceClaim {

    private String claimId;
    private double amount;
    private String claimStatus;

    private static final double PAYOUT_RATE = 0.85;

    public InsuranceClaim(String id, double claimAmount) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Claim ID cannot be null or empty");
        }
        if (claimAmount <= 0) {
            throw new IllegalArgumentException("Claim amount must be positive.");
        }
        this.claimId = id;
        this.amount = claimAmount;
        this.claimStatus = "Pending";
    }

    /**
     * Updates status only from Pending. Rejects null/invalid status.
     */
    public boolean processClaim(String statusUpdate) {
        // 1) Yêu cầu test: null phải ném IllegalArgumentException
        if (statusUpdate == null) {
            throw new IllegalArgumentException("statusUpdate must not be null");
        }

        // Chuẩn hóa tối thiểu (bỏ khoảng trắng đầu/cuối)
        String normalized = statusUpdate.trim();

        // 2) Chỉ chấp nhận 3 giá trị hợp lệ
        boolean isApproved = "Approved".equals(normalized);
        boolean isRejected = "Rejected".equals(normalized);
        boolean isPending  = "Pending".equals(normalized);

        if (!isApproved && !isRejected && !isPending) {
            throw new IllegalArgumentException("Invalid status: " + statusUpdate);
        }

        // 3) Chỉ cho đổi khi đang Pending
        if (!"Pending".equals(this.claimStatus)) {
            return false; // test testProcessClaimWhenNotPending kỳ vọng false
        }

        // Nếu yêu cầu đặt lại Pending → không đổi gì, vẫn coi là xử lý OK
        if (isApproved || isRejected) {
            this.claimStatus = normalized;
        }
        return true;
    }

    public double calculatePayout() {
        if ("Approved".equals(this.claimStatus)) {
            return amount * PAYOUT_RATE;
        } else {
            return 0;
        }
    }

    public void updateClaimAmount(double newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("New amount must be positive.");
        }
        this.amount = newAmount;
    }

    // Getters
    public String getClaimId() { return claimId; }
    public double getAmount() { return amount; }
    public String getClaimStatus() { return claimStatus; }

    @Override
    public String toString() {
        return "InsuranceClaim{" +
                "claimId='" + claimId + '\'' +
                ", amount=" + amount +
                ", claimStatus='" + claimStatus + '\'' +
                '}';
    }
}
