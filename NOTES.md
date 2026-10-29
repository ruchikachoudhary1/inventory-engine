Notes: reproducing and fixing the oversell
My answers after steps 4 and 5, 29 Oct 2026. The same text goes into NOTES.md in the repository.
1. Every shopper checked the stock first. Why did more than 100 still succeed?
Two shoppers could be inside reserve at the same time, and both passed the check on the same stale value before either subtracted. Then either both subtracted and the stock went to −1 (oversell), or both read the same value and wrote the same result, so the stock dropped once instead of twice (lost update).
2. Why did the numbers change from run to run?
All 200 shoppers are released at once, but they don't all run at once. The CPU has only a few cores, so only a few threads run truly in parallel and the rest take turns. Whether two of them land in the gap inside reserve at the same moment depends on that timing, which is different every run. Thread.yield() makes a pause in the gap more likely, but it is only a hint, so the overlap happens in some runs and not in others.
3. What exactly did synchronized change?
Only one shopper can be inside reserve at a time. The others wait at the lock on reserve, not at the start gate, until it leaves. So the check and the subtraction happen as one unbroken unit, and neither gap can occur.
Results