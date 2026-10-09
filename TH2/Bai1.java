
    import java.util.ArrayList;
import java.util.List;

class Block {
    private int index;
    private String data;
    private String prev_hash;
    private String hash;

    public  Block(int index, String data, String prev_hash) {
        this.index = index;
        this.data  = data;
        this.prev_hash = prev_hash;
        this.hash = calculateHash();
    }
    
    public  String calculateHash(){
        String input = index + "-" + data + "-" + prev_hash;
        return SimpleHash.hash(input);
    }
    
    public int getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return prev_hash;
    }

    public String getHash() {
        return hash;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public void display() {
        System.out.println("Index         : " + index);
        System.out.println("Data          : " + data);
        System.out.println("Previous Hash : " + prev_hash);
        System.out.println("Hash          : " + hash);
        System.out.println("----------------------------------------");
    }
}

class SimpleHash{
    private static final long MOD = 2_147_483_647L;
    
    public static String hash (String data){
        long h = 0;
        
        for (int i = 0; i< data.length(); i++){
            char c = data.charAt(i);
            h = (31 *h + c) %MOD;
        }
        return String.valueOf(h);
    }
}

 class Blockchain {

    private List<Block> chain;

    public Blockchain() {
        chain = new ArrayList<>();

        Block genesis = new Block(0, "Genesis Block", "0");
        chain.add(genesis);
    }

    public void addBlock(String data) {

        Block previousBlock = chain.get(chain.size() - 1);

        int newIndex = previousBlock.getIndex() + 1;
        String previousHash = previousBlock.getHash();

        Block newBlock = new Block(newIndex, data, previousHash);

        chain.add(newBlock);

        System.out.println("Da them block #" + newIndex);
    }

    public boolean isOK() {

        if (chain.isEmpty()) {
            System.out.println("Blockchain rong!");
            return false;
        }

        Block genesis = chain.get(0);

        if (genesis.getIndex() != 0
                || !genesis.getPreviousHash().equals("0")) {
            System.out.println("Genesis Block khong hop le!");
            return false;
        }

        for (int i = 0; i < chain.size(); i++) {

            Block current = chain.get(i);

            if (!current.getHash().equals(current.calculateHash())) {
                System.out.println(
                    "Hash khong hop le tai block #" + i
                );
                return false;
            }

            if (i > 0) {

                Block previous = chain.get(i - 1);

                if (current.getIndex() != previous.getIndex() + 1) {
                    System.out.println(
                        "Index khong hop le tai block #" + i
                    );
                    return false;
                }

                if (!current.getPreviousHash()
                        .equals(previous.getHash())) {
                    System.out.println(
                        "Previous hash khong hop le tai block #" + i
                    );
                    return false;
                }
            }
        }

        return true;
    }

    public void trace() {

        if (!isOK()) {
            System.out.println("Blockchain KHONG HOP LE!");
            return;
        }

        System.out.println("Blockchain HOP LE!");
        System.out.println("========== BLOCKCHAIN ==========");

        for (Block block : chain) {
            block.display();
        }
    }

    public Block getBlock(int index) {
        return chain.get(index);
    }
}
public class Bai1
{
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        blockchain.addBlock("Alice chuyen cho Bob 5 dong");
        blockchain.addBlock("Bob chuyen cho Lan 2 dong");
        blockchain.addBlock("Lan chuyen cho Minh 1 dong");

        System.out.println("\n===== LAN 1: BAN DAU =====");
        blockchain.trace();

        System.out.println("\n===== LAN 2: SUA DU LIEU =====");

        blockchain.getBlock(1).setData(
            "Alice chuyen cho Bob 500 dong"
        );

        blockchain.trace();
    }
}

