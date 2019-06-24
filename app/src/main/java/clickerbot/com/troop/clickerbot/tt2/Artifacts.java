package clickerbot.com.troop.clickerbot.tt2;

public enum Artifacts {
    BookOfShadows(Tier.SS),
    ChargedCard(Tier.A),
    StonesOfValrunes(Tier.S),
    ChestOfContentment(Tier.A),
    HeroicShield(Tier.A),
    BookOfProphecy(Tier.A),
    KhrysosBowl(Tier.A),
    ZakynthosCoin(Tier.F),
    GreatFayMedallion(Tier.A),
    NekoSculpture(Tier.A),
    CoinsOfEbizu(Tier.A),
    TheBronzedCompass(Tier.A),
    EvergrowingStack(Tier.A),
    FluteOfTheSoloist(Tier.S),
    HeavenlySword(Tier.A),
    DivineRetribution(Tier.A),
    DrunkenHammer(Tier.A),
    SamosekSword(Tier.A),
    TheRetaliator(Tier.A),
    StryfesPace(Tier.S),
    HerosBlade(Tier.A),
    TheSwordOfStorms(Tier.A),
    FuriesBow(Tier.A),
    CharmOfTheAncient(Tier.A),
    TinyTitanTree(Tier.A),
    HelmOfHermes(Tier.A),
    FruitOfEden(Tier.A),
    InfluentialElixir(Tier.A),
    ORyansCharm(Tier.A),
    HeartOfStorms(Tier.S),
    ApolloOrb(Tier.S),
    EaringsOfPortara(Tier.A),
    AvianFeather(Tier.F),
    CorruptedRuneHeart(Tier.F),
    DurendalSword(Tier.A),
    HelheimSkull(Tier.S),
    OathsBurden(Tier.A),
    CrownOfConstellation(Tier.A),
    TitaniasSceptre(Tier.A),
    FaginsGrip(Tier.A),
    RingOfCallisto(Tier.S),
    BladeOfDamocles(Tier.A),
    HelmetOfMadness(Tier.A),
    TitaniumPlating(Tier.A),
    MoonlightBracelet(Tier.A),
    AmethystStaff(Tier.A),
    SwordOfTheRoyals(Tier.A),
    SpearitsVigil(Tier.A),
    TheCobaltPlate(Tier.A),
    SigilsOfJudgement(Tier.A),
    FoliageOfTheKeeper(Tier.A),
    InvadersGjalarhorn(Tier.S),
    TitansMask(Tier.A),
    RoyalToxin(Tier.A),
    LaborersPendant(Tier.A),
    BringerOfRagnarok(Tier.A),
    ParchmentOfForesight(Tier.A),
    ElixirOfEden(Tier.A),
    Unkown(Tier.F);


    public Tier tier;
    public long colorSum;

    private Artifacts(Tier tier)
    {
        this.tier = tier;
    }

    public enum Tier
    {
        SS,
        S,
        A,
        F,
    }
}
