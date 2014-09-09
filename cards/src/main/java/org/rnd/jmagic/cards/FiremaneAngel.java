package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firemane Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3RWW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class FiremaneAngel extends Card
{
	public static final class FiremaneAngelAbility1 extends EventTriggeredAbility
	{
		public FiremaneAngelAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, if Firemane Angel is in your graveyard or on the battlefield, you may gain 1 life.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator inYourGraveyard = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(GraveyardOf.instance(You.instance())));
			this.canTrigger = Union.instance(inYourGraveyard, this.canTrigger);
			this.interveningIf = this.canTrigger;

			this.addEffect(youMay(gainLife(You.instance(), 1, "Gain 1 life"), "You may gain 1 life"));
		}
	}

	public static final class FiremaneAngelAbility2 extends ActivatedAbility
	{
		public FiremaneAngelAbility2(GameState state)
		{
			super(state, "(6)(R)(R)(W)(W): Return Firemane Angel from your graveyard to the battlefield. Activate this ability only during your upkeep.");
			this.setManaCost(new ManaPool("(6)(R)(R)(W)(W)"));

			EventFactory returnFiremane = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return Firemane Angel from your graveyard to the battlefield.");
			returnFiremane.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnFiremane.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			returnFiremane.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(returnFiremane);

			this.activateOnlyFromGraveyard();
			this.activateOnlyDuringYourUpkeep();
		}
	}

	public FiremaneAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Flying, first strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// At the beginning of your upkeep, if Firemane Angel is in your
		// graveyard or on the battlefield, you may gain 1 life.
		this.addAbility(new FiremaneAngelAbility1(state));

		// (6)(R)(R)(W)(W): Return Firemane Angel from your graveyard to the
		// battlefield. Activate this ability only during your upkeep.
		this.addAbility(new FiremaneAngelAbility2(state));
	}
}
