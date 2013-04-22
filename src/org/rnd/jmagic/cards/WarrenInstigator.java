package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warren Instigator")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.BERSERKER})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class WarrenInstigator extends Card
{
	public static final class LackeyProbablyTwice extends EventTriggeredAbility
	{
		public LackeyProbablyTwice(GameState state)
		{
			super(state, "Whenever Warren Instigator deals damage to an opponent, you may put a Goblin creature card from your hand onto the battlefield.");

			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));

			SetGenerator goblinCreatures = Intersect.instance(HasSubType.instance(SubType.GOBLIN), HasType.instance(Type.CREATURE));
			SetGenerator choices = Intersect.instance(InZone.instance(HandOf.instance(You.instance())), goblinCreatures);

			EventFactory putGoblinIntoPlay = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a Goblin creature card from your hand onto the battlefield");
			putGoblinIntoPlay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putGoblinIntoPlay.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putGoblinIntoPlay.parameters.put(EventType.Parameter.OBJECT, choices);

			this.addEffect(youMay(putGoblinIntoPlay, "You may put a Goblin creature card from your hand onto the battlefield."));
		}
	}

	public WarrenInstigator(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Double strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Whenever Warren Instigator deals damage to an opponent, you may put a
		// Goblin creature card from your hand onto the battlefield.
		this.addAbility(new LackeyProbablyTwice(state));
	}
}
