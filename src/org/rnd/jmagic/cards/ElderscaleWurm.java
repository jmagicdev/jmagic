package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elderscale Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4GGG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class ElderscaleWurm extends Card
{
	public static final class ElderscaleWurmAbility1 extends EventTriggeredAbility
	{
		public ElderscaleWurmAbility1(GameState state)
		{
			super(state, "When Elderscale Wurm enters the battlefield, if your life total is less than 7, your life total becomes 7.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourLifeTotalIsLessThanSeven = Intersect.instance(LifeTotalOf.instance(You.instance()), Between.instance(null, numberGenerator(6)));
			EventFactory setLife = new EventFactory(EventType.SET_LIFE, "Your life total becomes 7.");
			setLife.parameters.put(EventType.Parameter.CAUSE, This.instance());
			setLife.parameters.put(EventType.Parameter.NUMBER, numberGenerator(7));
			setLife.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(ifThen(yourLifeTotalIsLessThanSeven, setLife, "If your life total is less than 7, your life total becomes 7."));
		}
	}

	public static final class ElderscaleWurmAbility2 extends StaticAbility
	{
		public ElderscaleWurmAbility2(GameState state)
		{
			super(state, "As long as you have 7 or more life, damage that would reduce your life total to less than 7 reduces it to 7 instead.");

			// Don't need to set canApply here since having less than 7 life
			// means you can't reduce your life total to less than 7

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_REDUCE_LIFE_TOTAL_TO_LESS_THAN);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, numberGenerator(7));
			this.addEffectPart(part);
		}
	}

	public ElderscaleWurm(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Elderscale Wurm enters the battlefield, if your life total is
		// less than 7, your life total becomes 7.
		this.addAbility(new ElderscaleWurmAbility1(state));

		// As long as you have 7 or more life, damage that would reduce your
		// life total to less than 7 reduces it to 7 instead.
		this.addAbility(new ElderscaleWurmAbility2(state));
	}
}
