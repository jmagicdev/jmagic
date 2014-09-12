package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Living Totem")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT, SubType.ELEMENTAL})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class LivingTotem extends Card
{
	public static final class LivingTotemAbility1 extends EventTriggeredAbility
	{
		public LivingTotemAbility1(GameState state)
		{
			super(state, "When Living Totem enters the battlefield, you may put a +1/+1 counter on another target creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator creature = CreaturePermanents.instance();
			SetGenerator anothercreature = RelativeComplement.instance(creature, ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anothercreature, "another target creature"));
			this.addEffect(youMay(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on another target creature.")));
		}
	}

	public LivingTotem(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// When Living Totem enters the battlefield, you may put a +1/+1 counter
		// on another target creature.
		this.addAbility(new LivingTotemAbility1(state));
	}
}
