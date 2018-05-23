// Does not care about opponent... He is a big boy with big toys.
var dropMostPowerfulOne=function(knownCards){
    if(knownCards[0].length===0) return;
    var best=0;
    knownCards[0].forEach(function (item) { if(best<item) { best = item; } });

    return best;
};

var dropMostPowerfulOnee=function(knownCards){
    if(knownCards.length===0) return;
    var best=0;
    knownCards.forEach(function (item) { if(best<item) { best = item; } });

    return best;
};

// Takes in count presence of opponent. Not really a smart boy.
var makeABiasedMove=function (knownCards, enemyCards) {
    var best = dropMostPowerfulOne(knownCards);
    var worst = best;
    knownCards.forEach(function (item) { if(worst>item) { worst = item; } });

    var enemyBest = dropMostPowerfulOne(enemyCards);

    if(enemyBest>best)
        return worst;
    return best;
};

var makeItRain=function (knownCards, enemyCards) {
    var res = explore(knownCards[0],enemyCards[0],2,[0,0]);
    print(res);
    return res[1];
};

function explore(knownCards, enemyCards, depth, val) {

    if(depth===0) return 0;
    print(knownCards);
    var resVal=0;
    var best=knownCards[0];
    for(var i=0;i<depth;i++)
    {
        var item = knownCards[i];
        var res=0;
        knownCards.remove(i);

        var myBest=dropMostPowerfulOnee(knownCards);
        var hisBest=dropMostPowerfulOnee(enemyCards);
        if(myBest>hisBest) res+=1;
        else if (myBest<hisBest) res-=1;

        val[0]+=res;

        resVal = explore(knownCards, enemyCards, depth-1, val[0]);
        if(resVal>val) {val=resVal; best=item;}
        knownCards.add(item);
    }
    return [val[0], best];
}
