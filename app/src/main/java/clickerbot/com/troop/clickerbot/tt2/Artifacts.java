package clickerbot.com.troop.clickerbot.tt2;

import clickerbot.com.troop.clickerbot.R;

public enum Artifacts {
    BookOfShadows(Tier.SS, R.drawable._0),
    ChargedCard(Tier.A, R.drawable._1),
    StonesOfValrunes(Tier.S, R.drawable._2),
    ChestOfContentment(Tier.A, R.drawable._3),
    HeroicShield(Tier.A, R.drawable._4),
    BookOfProphecy(Tier.A, R.drawable._5),
    KhrysosBowl(Tier.A, R.drawable._6),
    ZakynthosCoin(Tier.F, R.drawable._7),
    GreatFayMedallion(Tier.A, R.drawable._8),
    NekoSculpture(Tier.A, R.drawable._9),
    CoinsOfEbizu(Tier.A, R.drawable._10),
    TheBronzedCompass(Tier.A, R.drawable._11),
    EvergrowingStack(Tier.A, R.drawable._12),
    FluteOfTheSoloist(Tier.S, R.drawable._13),
    HeavenlySword(Tier.A, R.drawable._14),
    DivineRetribution(Tier.A, R.drawable._15),
    DrunkenHammer(Tier.A, R.drawable._16),
    SamosekSword(Tier.A, R.drawable._17),
    TheRetaliator(Tier.A, R.drawable._18),
    StryfesPace(Tier.A, R.drawable._19),
    HerosBlade(Tier.A, R.drawable._20),
    TheSwordOfStorms(Tier.A, R.drawable._21),
    FuriesBow(Tier.A, R.drawable._22),
    CharmOfTheAncient(Tier.A, R.drawable._23),
    TinyTitanTree(Tier.A, R.drawable._24),
    HelmOfHermes(Tier.A, R.drawable._25),
    FruitOfEden(Tier.A, R.drawable._26),
    InfluentialElixir(Tier.A, R.drawable._27),
    ORyansCharm(Tier.A, R.drawable._28),
    HeartOfStorms(Tier.S, R.drawable._29),
    ApolloOrb(Tier.S, R.drawable._30),
    StrangeFruit(Tier.A, R.drawable._58),
    HadesOrb(Tier.A, R.drawable._59),
    EaringsOfPortara(Tier.S, R.drawable._31),
    AvianFeather(Tier.F, R.drawable._32),
    CorruptedRuneHeart(Tier.F, R.drawable._33),
    DurendalSword(Tier.A, R.drawable._34),
    HelheimSkull(Tier.S, R.drawable._35),
    OathsBurden(Tier.A, R.drawable._36),
    CrownOfConstellation(Tier.A, R.drawable._37),
    TitaniasSceptre(Tier.A, R.drawable._38),
    FaginsGrip(Tier.A, R.drawable._39),
    RingOfCallisto(Tier.S, R.drawable._40),
    BladeOfDamocles(Tier.A, R.drawable._41),
    HelmetOfMadness(Tier.A, R.drawable._42),
    TitaniumPlating(Tier.A, R.drawable._43),
    MoonlightBracelet(Tier.A, R.drawable._44),
    AmethystStaff(Tier.A, R.drawable._45),
    SwordOfTheRoyals(Tier.A, R.drawable._46),
    SpearitsVigil(Tier.A, R.drawable._47),
    TheCobaltPlate(Tier.A, R.drawable._48),
    SigilsOfJudgement(Tier.A, R.drawable._49),
    FoliageOfTheKeeper(Tier.A, R.drawable._50),
    InvadersGjalarhorn(Tier.S, R.drawable._51),
    TitansMask(Tier.A, R.drawable._52),
    RoyalToxin(Tier.A, R.drawable._53),
    LaborersPendant(Tier.A, R.drawable._54),
    BringerOfRagnarok(Tier.A, R.drawable._55),
    ParchmentOfForesight(Tier.A, R.drawable._56),
    ElixirOfEden(Tier.A, R.drawable._57),
    Unkown(Tier.F, R.drawable.hero_button_gray);


    public Tier tier;
    public int image_id;

    private Artifacts(Tier tier, int image_id)
    {
        this.tier = tier;
        this.image_id = image_id;
    }

    public enum Tier
    {
        SS,
        S,
        A,
        F,
    }
}
