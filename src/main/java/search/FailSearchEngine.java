package search;

import exceptions.DataNotFoundException;
import servers.Failable;

import java.util.Optional;

public class FailSearchEngine {

    private int count;
    private Result result = new Result();

    public Result findFail(Failable currentFailable) {
        int start = 0;
        int end = currentFailable.getSize() - 1; // Lets start from 0 index

        int mid = 0, failedIndex;
        boolean isFailed = false;

        while (end - start > 1) {
            count++;
            mid = (start + end) / 2;
            isFailed = currentFailable.getInnerFailable(mid).isFailed();
            if (isFailed) {
                end = mid;
            } else {
                start = mid;
            }
        }

        // Verify boundary conditions
        if (isFailed) {
            if (count == 1) {
                failedIndex = verifyBoundaryCondition(currentFailable, start, mid);
            } else {
                failedIndex = verifyBoundaryCondition(currentFailable, start, mid);
            }
        } else {
            failedIndex = end;
        }

        Failable inner = currentFailable.getInnerFailable(failedIndex);
        boolean hasInner = inner != null && inner.getInnerFailable(0) != null;
        if (hasInner) {
            findFail(inner);
        } else {
            System.out.println("We've reached last nested failed element (Node): in " + count + " iterations");
            result.setFailedServer(Optional.ofNullable(inner).isPresent() ? inner.getParentId() : -1);
            result.setFailedNode(Optional.ofNullable(inner).isPresent() ? inner.getId() : -1);
        }

        if (result.isEmpty()) {
            throw new DataNotFoundException("Cannot find disconnected node!");
        }
        return result;
    }

    private int verifyBoundaryCondition(Failable currentFailable, int start, int mid) {
        int failedIndex;
        boolean isFailed = currentFailable.getInnerFailable(start).isFailed();
        if (isFailed) {
            failedIndex = start;
        } else {
            failedIndex = mid;
        }
        return failedIndex;
    }

    public Result findFailIterativelly(Failable currentFailable) {
        boolean isFailed;
        for (int i = 0; i < currentFailable.getSize(); i++) {
            count++;
            Failable inner = currentFailable.getInnerFailable(i);
            isFailed = currentFailable.getInnerFailable(i).isFailed();
            if (isFailed) {
                boolean hasInner = inner != null && inner.getInnerFailable(0) != null;
                if (hasInner) {
                    findFailIterativelly(inner);
                    break;
                } else {
                    System.out.println("We've reached last nested failed element (Node) in " + count + " iterations");
                    result.setFailedServer(Optional.ofNullable(inner).isPresent() ? inner.getParentId() : -1);
                    result.setFailedNode(Optional.ofNullable(inner).isPresent() ? inner.getId() : -1);
                    break;
                }
            }
        }
        if (result.isEmpty()) {
            throw new DataNotFoundException("Cannot find disconnected node!");
        }
        return result;
    }
}
