package fa.nfa;

import fa.State;

import java.util.*;

public class NFA implements NFAInterface{

    // instance variables
    private Set<NFAState> states;// should be able to use an iterator to "get" states in order they were added
    private Set<Character> sigma;
    private State start;
    private Set<State> finalStates;

    // constructor
    public NFA() {
        states  = new LinkedHashSet<>();
        sigma = new LinkedHashSet<Character>();
        sigma.add('e');
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

        if(s.toStates('e').isEmpty()) return Set.of();

        Stack<NFAState> stack = new Stack<>();
        Set<NFAState> retSet = new HashSet<>();

        stack.push(s);

        while (!stack.isEmpty()) {
            
        }
    }

    @Override
    public int maxCopies(String s) {
        return 0;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {

        // check first if symbol is in alphabet
        if (!inSigma(onSymb)) return false;
        // check if fromState is in states
        if (!inQ(fromState)) return false;

        // check if each state in toStates is in states
        Set<NFAState> transitionSet = new HashSet<>();
        for (String s : toStates) {
            if(inQ(s)) transitionSet.add(getState(s));
        }
        // test if transitionSet is empty to make sure that toStates is valid
        if (transitionSet.isEmpty()) return false;

        getState(fromState).addTransition(transitionSet, onSymb);

        return true;
    }

    @Override
    public boolean isDFA() {
        return false;
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
        return false;
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
