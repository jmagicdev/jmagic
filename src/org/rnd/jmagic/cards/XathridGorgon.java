package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Xathrid Gorgon")
@Types({Type.CREATURE})
@SubTypes({SubType.GORGON})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class XathridGorgon extends Card
{
	public static final class XathridGorgonAbility1 extends ActivatedAbility
	{
		public XathridGorgonAbility1(GameState state)
		{
			super(state, "(2)(B), (T): Put a petrification counter on target creature. It gains defender and becomes a colorless artifact in addition to its other types. Its activated abilities can't be activated.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			this.addEffect(putCounters(1, Counter.CounterType.PETRIFICATION, target, "Put a petrification counter on target creature."));

			ContinuousEffect.Part ability = addAbilityToObject(target, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Defender.class));

			ContinuousEffect.Part color = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
			color.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			color.parameters.put(ContinuousEffectType.Parameter.COLOR, Empty.instance());

			ContinuousEffect.Part type = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			type.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			type.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ARTIFACT));

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(target));

			ContinuousEffect.Part prohibit = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibit.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));

			this.addEffect(createFloatingEffect(Empty.instance(), "It gains defender and becomes a colorless artifact in addition to its other types. Its activated abilities can't be activated.", ability, color, type, prohibit));
		}
	}

	public XathridGorgon(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(6);

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// (2)(B), (T): Put a petrification counter on target creature. It gains
		// defender and becomes a colorless artifact in addition to its other
		// types. Its activated abilities can't be activated. (A creature with
		// defender can't attack.)
		this.addAbility(new XathridGorgonAbility1(state));
	}
}
