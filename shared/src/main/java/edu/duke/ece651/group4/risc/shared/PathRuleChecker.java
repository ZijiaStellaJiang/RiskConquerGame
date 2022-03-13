package edu.duke.ece651.group4.risc.shared;

import java.util.HashSet;
import java.util.Stack;

public class PathRuleChecker<T> extends ActionRuleChecker<T> {
    public PathRuleChecker(ActionRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        Stack<String> stack = new Stack<>();
        HashSet<String> visited = new HashSet<>();
        String source = parse.getSource();
        String dest = parse.getDest();
        stack.push(source);
        visited.add(source);
        while (!stack.empty()){
            if(stack.peek().equals(dest)){
                return null;
            }
            for(Territory<T> t: p.getMyTerritories()){
                if(t.getName().toUpperCase().equals(stack.peek())){
                    stack.pop();
                    for(Territory<T> fromT: t.getMyNeigh()){
                        if(!visited.contains(fromT.getName().toUpperCase()) &&
                        p.getMyTerritories().contains(fromT)){
                            stack.push(fromT.getName().toUpperCase());
                            visited.add(fromT.getName().toUpperCase());
                        }
                    }
                    break;
                }
            }
        }
        return "That action is invalid: can not form a path from source to destination";
    }
}
