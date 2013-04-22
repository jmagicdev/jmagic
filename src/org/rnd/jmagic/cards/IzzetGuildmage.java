package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Izzet Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("(U/R)(U/R)")
@Printings({@Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetGuildmage extends Card
{
	public static final class IzzetGuildmageAbility0 extends ActivatedAbility
	{
		public IzzetGuildmageAbility0(GameState state)
		{
			super(state, "(2)(U): Copy target instant spell you control with converted mana cost 2 or less. You may choose new targets for the copy.");
			this.setManaCost(new ManaPool("(2)(U)"));

			SetGenerator youControl = ControlledBy.instance(You.instance(), Stack.instance());
			SetGenerator convertedTwoOrLess = HasConvertedManaCost.instance(Between.instance(null, 2));
			SetGenerator targetableSpells = Intersect.instance(HasType.instance(Type.INSTANT), Spells.instance(), youControl, convertedTwoOrLess);
			SetGenerator target = targetedBy(this.addTarget(targetableSpells, "target instant spell you control with converted mana cost 2 or less"));
			EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy target instant spell you control with converted mana cost 2 or less. You may choose new targets for the copy.");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, target);
			copy.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(copy);
		}
	}

	public static final class IzzetGuildmageAbility1 extends ActivatedAbility
	{
		public IzzetGuildmageAbility1(GameState state)
		{
			super(state, "(2)(R): Copy target sorcery spell you control with converted mana cost 2 or less. You may choose new targets for the copy.");
			this.setManaCost(new ManaPool("(2)(R)"));

			SetGenerator youControl = ControlledBy.instance(You.instance(), Stack.instance());
			SetGenerator convertedTwoOrLess = HasConvertedManaCost.instance(Between.instance(null, 2));
			SetGenerator targetableSpells = Intersect.instance(HasType.instance(Type.SORCERY), Spells.instance(), youControl, convertedTwoOrLess);
			SetGenerator target = targetedBy(this.addTarget(targetableSpells, "target sorcery spell you control with converted mana cost 2 or less"));
			EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy target sorcery spell you control with converted mana cost 2 or less. You may choose new targets for the copy.");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, target);
			copy.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(copy);
		}
	}

	public IzzetGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2)(U): Copy target instant spell you control with converted mana
		// cost 2 or less. You may choose new targets for the copy.
		this.addAbility(new IzzetGuildmageAbility0(state));

		// (2)(R): Copy target sorcery spell you control with converted mana
		// cost 2 or less. You may choose new targets for the copy.
		this.addAbility(new IzzetGuildmageAbility1(state));
	}
}
