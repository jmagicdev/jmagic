package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Archaeomancer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Archaeomancer extends Card
{
	public static final class ArchaeomancerAbility0 extends EventTriggeredAbility
	{
		public ArchaeomancerAbility0(GameState state)
		{
			super(state, "When Archaeomancer enters the battlefield, return target instant or sorcery card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance()))), "target instant or sorcery card from your graveyard"));

			EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target instant or sorcery card from your graveyard to your hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(factory);
		}
	}

	public Archaeomancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// When Archaeomancer enters the battlefield, return target instant or
		// sorcery card from your graveyard to your hand.
		this.addAbility(new ArchaeomancerAbility0(state));
	}
}
