package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Asceticism")
@Types({Type.ENCHANTMENT})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Asceticism extends Card
{
	public static final class AsceticismAbility0 extends StaticAbility
	{
		public AsceticismAbility0(GameState state)
		{
			super(state, "Creatures you control have hexproof.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Hexproof.class)));
			this.addEffectPart(part);
		}
	}

	public static final class AsceticismAbility1 extends ActivatedAbility
	{
		public AsceticismAbility1(GameState state)
		{
			super(state, "(1)(G): Regenerate target creature.");
			this.setManaCost(new ManaPool("(1)(G)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(regenerate(target, "Regenerate target creature."));
		}
	}

	public Asceticism(GameState state)
	{
		super(state);

		// Creatures you control have hexproof
		this.addAbility(new AsceticismAbility0(state));

		// (1)(G): Regenerate target creature.
		this.addAbility(new AsceticismAbility1(state));
	}
}
