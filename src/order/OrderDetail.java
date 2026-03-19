package order;

public class OrderDetail extends BaseEntity {

    private String detailType;

    public OrderDetail(int id, String detailType) {
        super(id);
        this.detailType = detailType;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }
}