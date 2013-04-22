package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Goblin Battle Jester")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinBattleJester extends Card
{
	public static final class GoblinBattleJesterAbility0 extends EventTriggeredAbility
	{
		public GoblinBattleJesterAbility0(GameState state)
		{
			super(state, "Whenever you cast a red spell, target creature can't block this turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.RED), Spells.instance()));
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(target, Blocking.instance())));
			this.addEffect(createFloatingEffect("Target creature can't block this turn", part));
		}
	}

	public GoblinBattleJester(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever you cast a red spell, target creature can't block this turn.
		this.addAbility(new GoblinBattleJesterAbility0(state));
	}
}
