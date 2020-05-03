package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.Invocation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class CchProcessor {

    private static List<CchProcessor> processors = new ArrayList<>();

    public abstract Object process(Invocation invocation);

    public Object invokeNext(CchProcessor curProcessor, Invocation invocation) {
        boolean find = false;
        for (CchProcessor processor : processors) {
            if (find) {
                return processor.process(invocation);
            }
            if (curProcessor.getClass() == processor.getClass()) {
                find = true;
            }
        }
        return null;
    }

    public static List<CchProcessor> getProcessors() {
        return processors;
    }

}
