package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thassa, God of the Sea")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ThassaGodoftheSea extends Card
{
	public static final class ThassaGodoftheSeaAbility1 extends StaticAbility
	{
		public ThassaGodoftheSeaAbility1(GameState state)
		{
			super(state, "As long as your devotion to blue is less than five, Thassa isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 4), DevotionTo.instance(Color.BLUE));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class ThassaGodoftheSeaAbility2 extends EventTriggeredAbility
	{
		public ThassaGodoftheSeaAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, scry 1.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public static final class ThassaGodoftheSeaAbility3 extends ActivatedAbility
	{
		public ThassaGodoftheSeaAbility3(GameState state)
		{
			super(state, "(1)(U): Target creature you control can't be blocked this turn.");
			this.setManaCost(new ManaPool("1U"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(createFloatingEffect("Target creature you control can't be blocked this turn.", unblockable(target)));
		}
	}

	public ThassaGodoftheSea(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to blue is less than five, Thassa isn't a
		// creature. (Each {U} in the mana costs of permanents you control
		// counts toward your devotion to blue.)
		this.addAbility(new ThassaGodoftheSeaAbility1(state));

		// At the beginning of your upkeep, scry 1.
		this.addAbility(new ThassaGodoftheSeaAbility2(state));

		// {1}{U}: Target creature you control can't be blocked this turn.
		this.addAbility(new ThassaGodoftheSeaAbility3(state));
	}
}
