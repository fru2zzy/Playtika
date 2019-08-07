package search;

import exceptions.DataNotFoundException;
import servers.Failable;
import utils.CustomOptional;

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
            CustomOptional<Failable> inner = currentFailable.getInnerFallible(mid);
            if (inner.isPresent()) {
                isFailed = inner.get().isFailed();
            }
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

        Failable inner = (Failable) currentFailable.getInnerFallible(failedIndex).get();
        boolean hasInner = inner != null && inner.getInnerFallible(0) != null;
        if (hasInner) {
            findFail(inner);
        } else {
            System.out.println("We've reached last nested failed element (Node): in " + count + " iterations");
            result.setFailedServer(inner.getParentId());
            result.setFailedNode(inner.getId());
        }

        if (result.isEmpty()) {
            throw new DataNotFoundException("Cannot find disconnected node!");
        }
        return result;
    }

    private int verifyBoundaryCondition(Failable currentFailable, int start, int mid) {
        int failedIndex = 0;
        CustomOptional<Failable> inner = currentFailable.getInnerFallible(mid);
        if (inner.isPresent()) {
            boolean isFailed = inner.get().isFailed();
            if (isFailed) {
                failedIndex = start;
            } else {
                failedIndex = mid;
            }
            return failedIndex;
        }
        return failedIndex;
    }

    public Result findFailIterativelly(Failable currentFailable) {
        boolean isFailed;
        for (int i = 0; i < currentFailable.getSize(); i++) {
            count++;
            CustomOptional<Failable> inner = currentFailable.getInnerFallible(i);
            if (inner.isPresent()) {
                isFailed = inner.get().isFailed();
                if (isFailed) {
                    boolean hasInner = inner.get().getInnerFallible(0) != null;
                    if (hasInner) {
                        findFailIterativelly(inner.get());
                        break;
                    } else {
                        System.out.println("We've reached last nested failed element (Node) in " + count + " iterations");
                        result.setFailedServer(inner.get().getParentId());
                        result.setFailedNode(inner.get().getId());
                        break;
                    }
                }
            }
        }
        if (result.isEmpty()) {
            throw new DataNotFoundException("Cannot find disconnected node!");
        }
        return result;
    }
}
