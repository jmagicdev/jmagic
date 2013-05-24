package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Treasure Hunter")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class TreasureHunter extends Card
{
	public static final class IndianaJones extends EventTriggeredAbility
	{
		public IndianaJones(GameState state)
		{
			super(state, "When Treasure Hunter enters the battlefield, you may return target artifact card from your graveyard to your hand.");

			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator graveyard = GraveyardOf.instance(You.instance());

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(graveyard)), "target artifact card from your graveyard");

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target artifact card from your graveyard to your hand");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			this.addEffect(youMay(move, "You may return target artifact card from your graveyard to your hand."));
		}
	}

	public TreasureHunter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new IndianaJones(state));
	}
}
