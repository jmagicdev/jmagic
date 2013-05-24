package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mirran Spy")
@Types({Type.CREATURE})
@SubTypes({SubType.DRONE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MirranSpy extends Card
{
	public static final class MirranSpyAbility1 extends EventTriggeredAbility
	{
		public MirranSpyAbility1(GameState state)
		{
			super(state, "Whenever you cast an artifact spell, you may untap target creature.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasType.instance(Type.ARTIFACT)));
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(untap(target, "Untap target creature."), "You may untap target creature."));
		}
	}

	public MirranSpy(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast an artifact spell, you may untap target creature.
		this.addAbility(new MirranSpyAbility1(state));
	}
}
