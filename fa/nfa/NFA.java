package fa.nfa;

import fa.State;

import java.util.*;

public class NFA implements NFAInterface{

    // instance variables
    private Set<NFAState> states;// should be able to use an iterator to "get" states in order they were added
    private Set<Character> sigma;
    private NFAState start;
    private Set<State> finalStates;

    // constructor
    public NFA() {
        states  = new LinkedHashSet<>();
        sigma = new LinkedHashSet<Character>();
        //sigma.add('e');
        finalStates = new LinkedHashSet<State>();
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // check first if symbol is in alphabet
        if (!inSigma(onSymb)) return Set.of();

        // check if fromState is in states
        if (!states.contains(from)) return Set.of();

        return from.toStates(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {

        if(s.toStates('e').isEmpty()) return Set.of(s);

        Stack<NFAState> stack = new Stack<>();
        Set<NFAState> visited = new LinkedHashSet<>();

        stack.push(s);
        while (!stack.isEmpty()) {
           NFAState current = stack.pop();
           visited.add(current);
           for (NFAState state : current.toStates('e')) {
               if (!visited.contains(state)) {
                   stack.push(state);
               }
           }
        }

        return visited;
    }

    @Override
    public int maxCopies(String s) {
        int numCopies = 0;
        Set<TraceStep> copies = new LinkedHashSet<>();

        TraceStep startStep = new TraceStep(start, s);
        copies.add(startStep);
        copies.addAll(startStep.getEpsilonClosures());
        if (copies.size() > numCopies) numCopies = copies.size();

        while (!copies.isEmpty()) {
            Set<TraceStep> newCopies = new LinkedHashSet<>();
            for (TraceStep copy : copies) {
                Set<TraceStep> nextSteps = copy.getNextSteps();
                newCopies.addAll(nextSteps);

                for (TraceStep nextStep : nextSteps) {
                    newCopies.addAll(nextStep.getEpsilonClosures());
                }
            }

            copies = newCopies;
            if (copies.size() > numCopies) numCopies = copies.size();
        }

        return numCopies;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {

        // check first if symbol is in alphabet
        if (!inSigma(onSymb) && onSymb != 'e') return false;
        // check if fromState is in states
        if (!inQ(fromState)) return false;

        // check if each state in toStates is in states
        Set<NFAState> transitionSet = new LinkedHashSet<>();
        for (String s : toStates) {
            if(inQ(s)) transitionSet.add(getState(s));
        }
        if (transitionSet.size() != toStates.size()) return false;

        getState(fromState).addTransition(transitionSet, onSymb);

        return true;
    }

    @Override
    public boolean isDFA() {
        for (NFAState state : states) {
           if (!eClosure(state).isEmpty()) {
               return false;
           }

           for (char alphabetChar : sigma){
               if (getToState(state, alphabetChar).size() > 1) {
                   return false;
               }
           }
        }

        return true;
    }

    @Override
    public boolean addState(String name) {
        if(!inQ(name)) {
            NFAState state = new NFAState(name);
            states.add(state);
            return true;
        }
        return false;
    }

    @Override
    public boolean setFinal(String name) {
        if (inQ(name) && !(finalStates.contains(getState(name)))) {
            finalStates.add(getState(name));
            return true;
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        if (inQ(name)) {
            start = getState(name);
            return true;
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        Set<TraceStep> copies = new LinkedHashSet<>();

        TraceStep startStep = new TraceStep(start, s);
        copies.add(startStep);
        copies.addAll(startStep.getEpsilonClosures());

        while (!copies.isEmpty()) {
            for (TraceStep copy : copies) {
                if (copy.getRemaining().isEmpty() && isFinal(copy.getCurrentState().getName())) {
                    return true;
                }
            }

            Set<TraceStep> newCopies = new LinkedHashSet<>();
            for (TraceStep copy : copies) {
                Set<TraceStep> nextSteps = copy.getNextSteps();
                newCopies.addAll(nextSteps);

                for (TraceStep nextStep : nextSteps) {
                    newCopies.addAll(nextStep.getEpsilonClosures());
                }
            }

            copies = newCopies;
        }

        return false;
    }

    private class TraceStep {
        private NFAState currentState;
        private String remaining;

        public TraceStep(NFAState currentState, String remaining) {
            this.currentState = currentState;
            this.remaining = remaining;
        }

        public Set<TraceStep> getNextSteps() {
            Set<TraceStep> nextSteps = new LinkedHashSet<>();

            if (remaining.isEmpty()) return nextSteps;

            for (NFAState state : getToState(currentState, remaining.charAt(0))) {
                nextSteps.add(new TraceStep(state, remaining.substring(1)));
            }

            return nextSteps;
        }

        public Set<TraceStep> getEpsilonClosures() {
            Set<TraceStep> epsilonClosures = new LinkedHashSet<>();

            for (NFAState state : eClosure(currentState)) {
                epsilonClosures.add(new TraceStep(state, remaining));
            }

            return epsilonClosures;
        }

        public NFAState getCurrentState() {
            return currentState;
        }

        public String getRemaining() {
            return remaining;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TraceStep traceStep) {
                return currentState.equals(traceStep.getCurrentState()) && remaining.equals(traceStep.getRemaining());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return (currentState.getName() + remaining).hashCode();
        }
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public NFAState getState(String name) {
        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) {
            NFAState state = iter.next();
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(getState(name));
    }

    @Override
    public boolean isStart(String name) {
        return start.getName().equals(name);
    }

    // helper functions
    // takes stateName and uses Set contains method check if state is in states
    private boolean inQ(String stateName) {
        NFAState state = new NFAState(stateName);
        return states.contains(state);
    }

    // checks if sigma contains char
    // takes symbol and uses Set contains method check if symbol is in alphabet
    private boolean inSigma(char symbol) {
        return sigma.contains(symbol);
    }
}
