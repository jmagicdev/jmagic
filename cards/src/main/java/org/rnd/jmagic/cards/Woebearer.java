package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Woebearer")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Woebearer extends Card
{
	public static final class MeleeRaiseDead extends EventTriggeredAbility
	{
		public MeleeRaiseDead(GameState state)
		{
			super(state, "Whenever Woebearer deals combat damage to a player, you may return target creature card from your graveyard to your hand.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card from your graveyard");

			EventFactory raiseDead = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to your hand");
			raiseDead.parameters.put(EventType.Parameter.CAUSE, This.instance());
			raiseDead.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			raiseDead.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(youMay(raiseDead, "You may return target creature card from your graveyard to your hand."));
		}
	}

	public Woebearer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Fear
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));

		// Whenever Woebearer deals combat damage to a player, you may return
		// target creature card from your graveyard to your hand.
		this.addAbility(new MeleeRaiseDead(state));
	}
}
