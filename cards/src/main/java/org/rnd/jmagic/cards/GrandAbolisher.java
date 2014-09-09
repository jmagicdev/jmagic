package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Grand Abolisher")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class GrandAbolisher extends Card
{
	public static final class GrandAbolisherAbility0 extends StaticAbility
	{
		public GrandAbolisherAbility0(GameState state)
		{
			super(state, "During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments.");

			SetGenerator duringYourTurn = Intersect.instance(CurrentTurn.instance(), TurnOf.instance(You.instance()));
			this.canApply = Both.instance(this.canApply, duringYourTurn);

			MultipleSetPattern stuff = new MultipleSetPattern(false);
			stuff.addPattern(SetPattern.CASTABLE);
			stuff.addPattern(new SimpleSetPattern(AbilitiesOf.instance(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), EnchantmentPermanents.instance()))));

			SimpleEventPattern doStuff = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			doStuff.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			doStuff.put(EventType.Parameter.OBJECT, stuff);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(doStuff));
			this.addEffectPart(part);
		}
	}

	public GrandAbolisher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// During your turn, your opponents can't cast spells or activate
		// abilities of artifacts, creatures, or enchantments.
		this.addAbility(new GrandAbolisherAbility0(state));
	}
}
