package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dual Casting")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class DualCasting extends Card
{
	public static final class DualCast extends ActivatedAbility
	{
		public DualCast(GameState state)
		{
			super(state, "(R), (T): Copy target instant or sorcery spell you control. You may choose new targets for the copy.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), ControlledBy.instance(You.instance(), Stack.instance())), "target instant or sorcery spell you control"));

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy target instant or sorcery spell you control. You may choose new targets for the copy.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public static final class DualCastingAbility1 extends StaticAbility
	{
		public DualCastingAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(R), (T): Copy target instant or sorcery spell you control. You may choose new targets for the copy.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), DualCast.class));
		}
	}

	public DualCasting(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "(R), (T): Copy target instant or sorcery spell you control. You may choose new targets for the copy."
		this.addAbility(new DualCastingAbility1(state));
	}
}
