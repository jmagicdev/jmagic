package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Conquering Manticore")
@Types({Type.CREATURE})
@SubTypes({SubType.MANTICORE})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ConqueringManticore extends Card
{
	public static final class ThreateningEntrance extends EventTriggeredAbility
	{
		public ThreateningEntrance(GameState state)
		{
			super(state, "When Conquering Manticore enters the battlefield, gain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls");

			this.addEffect(untap(targetedBy(target), "Untap target creature"));

			ContinuousEffect.Part part1 = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part1.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part1.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			ContinuousEffect.Part part2 = addAbilityToObject(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class);

			this.addEffect(createFloatingEffect("and gain control of it until end of turn. That creature gains haste until end of turn.", part1, part2));
		}
	}

	public ConqueringManticore(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Conquering Manticore enters the battlefield, gain control of
		// target creature an opponent controls until end of turn. Untap that
		// creature. It gains haste until end of turn.
		this.addAbility(new ThreateningEntrance(state));
	}
}
