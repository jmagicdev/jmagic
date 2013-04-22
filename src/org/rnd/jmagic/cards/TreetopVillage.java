package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Treetop Village")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class TreetopVillage extends Card
{
	public static final class AnimateTreetop extends ActivatedAbility
	{
		public AnimateTreetop(GameState state)
		{
			super(state, "(1)(G): Treetop Village becomes a 3/3 green Ape creature with trample until end of turn. It's still a land.");

			this.setManaCost(new ManaPool("1G"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 3, 3);
			animator.addColor(Color.GREEN);
			animator.addSubType(SubType.APE);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
			this.addEffect(createFloatingEffect("Treetop Village becomes a 3/3 green Ape creature with trample until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public TreetopVillage(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
		this.addAbility(new AnimateTreetop(state));
	}
}
