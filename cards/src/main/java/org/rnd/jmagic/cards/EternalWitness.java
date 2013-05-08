package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eternal Witness")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class EternalWitness extends Card
{
	public static final class EternalWitnessAbility0 extends EventTriggeredAbility
	{
		public EternalWitnessAbility0(GameState state)
		{
			super(state, "When Eternal Witness enters the battlefield, you may return target card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator graveyard = GraveyardOf.instance(You.instance());
			SetGenerator target = targetedBy(this.addTarget(InZone.instance(graveyard), "target card from your graveyard"));

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target card from your graveyard to your hand");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			move.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(youMay(move, "You may return target card from your graveyard to your hand."));
		}
	}

	public EternalWitness(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Eternal Witness enters the battlefield, you may return target
		// card from your graveyard to your hand.
		this.addAbility(new EternalWitnessAbility0(state));
	}
}
