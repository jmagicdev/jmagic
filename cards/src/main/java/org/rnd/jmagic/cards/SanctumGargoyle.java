package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sanctum Gargoyle")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GARGOYLE})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SanctumGargoyle extends Card
{
	public static final class SlightlyLateRescueGargoyle extends EventTriggeredAbility
	{
		public SlightlyLateRescueGargoyle(GameState state)
		{
			super(state, "When Sanctum Gargoyle enters the battlefield, you may return target artifact card from your graveyard to your hand.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "target artifact card from your graveyard");

			EventFactory moveFactory = new EventFactory(EventType.MOVE_OBJECTS, "Return target artifact card from your graveyard to your hand");
			moveFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveFactory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			moveFactory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			this.addEffect(youMay(moveFactory, "You may return target artifact card from your graveyard to your hand."));
		}
	}

	public SanctumGargoyle(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new SlightlyLateRescueGargoyle(state));
	}
}
