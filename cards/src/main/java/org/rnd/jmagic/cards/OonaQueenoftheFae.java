package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oona, Queen of the Fae")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.WIZARD})
@ManaCost("3(U/B)(U/B)(U/B)")
@Printings({@Printings.Printed(ex = Expansion.FTV_LEGENDS, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class OonaQueenoftheFae extends Card
{
	public static final class OonaQueenoftheFaeAbility1 extends ActivatedAbility
	{
		public OonaQueenoftheFaeAbility1(GameState state)
		{
			super(state, "(X)(UB): Choose a color. Target opponent exiles the top X cards of his or her library. For each card of the chosen color exiled this way, put a 1/1 blue and black Faerie Rogue creature token with flying onto the battlefield.");
			this.setManaCost(new ManaPool("X(UB)"));

			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

			SetGenerator cards = TopCards.instance(ValueOfX.instance(This.instance()), LibraryOf.instance(targetedBy(target)));

			EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a color.");
			choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
			choose.parameters.put(EventType.Parameter.CHOICE, Identity.instance(Color.allColors()));
			choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR));
			choose.parameters.put(EventType.Parameter.OBJECT, This.instance());
			this.addEffect(choose);

			EventFactory exile = exile(cards, "Target opponent exiles the top X cards of his or her library.");
			this.addEffect(exile);

			SetGenerator cardsExiledThisWay = NewObjectOf.instance(EffectResult.instance(exile));
			SetGenerator cardsOfTheChosenColor = HasColor.instance(EffectResult.instance(choose));
			SetGenerator number = Count.instance(Intersect.instance(cardsExiledThisWay, cardsOfTheChosenColor));
			String effectName = "For each card of the chosen color exiled this way, put a 1/1 blue and black Faerie Rogue creature token with flying onto the battlefield";
			CreateTokensFactory tokens = new CreateTokensFactory(number, numberGenerator(1), numberGenerator(1), effectName);
			tokens.setColors(Color.BLUE, Color.BLACK);
			tokens.setSubTypes(SubType.FAERIE, SubType.ROGUE);
			tokens.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public OonaQueenoftheFae(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (X)((u/b)): Choose a color. Target opponent exiles the top X cards of
		// his or her library. For each card of the chosen color exiled this
		// way, put a 1/1 blue and black Faerie Rogue creature token with flying
		// onto the battlefield.
		this.addAbility(new OonaQueenoftheFaeAbility1(state));
	}
}
