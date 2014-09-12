package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern;

@Name("Kapsho Kitefins")
@Types({Type.CREATURE})
@SubTypes({SubType.FISH})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class KapshoKitefins extends Card
{
	public static final class KapshoKitefinsAbility1 extends EventTriggeredAbility
	{
		public KapshoKitefinsAbility1(GameState state)
		{
			super(state, "Whenever Kapsho Kitefins or another creature enters the battlefield under your control, tap target creature an opponent controls.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), ControllerOf.instance(ABILITY_SOURCE_OF_THIS), false));

			SetGenerator opponentsCreatures = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(opponentsCreatures, "target creature an opponent controls"));
			this.addEffect(tap(target, "Tap target creature an opponent controls."));
		}
	}

	public KapshoKitefins(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Kapsho Kitefins or another creature enters the battlefield
		// under your control, tap target creature an opponent controls.
		this.addAbility(new KapshoKitefinsAbility1(state));
	}
}
