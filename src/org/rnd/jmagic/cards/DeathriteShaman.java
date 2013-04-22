package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deathrite Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ELF})
@ManaCost("(B/G)")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class DeathriteShaman extends Card
{
	public static final class DeathriteShamanAbility0 extends ActivatedAbility
	{
		public DeathriteShamanAbility0(GameState state)
		{
			super(state, "(T): Exile target land card from a graveyard. Add one mana of any color to your mana pool.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.LAND), InZone.instance(GraveyardOf.instance(Players.instance()))), "target land card in a graveyard"));
			this.addEffect(exile(target, "Exile target land card from a graveyard."));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)", "Add one mana of any color to your mana pool."));
		}
	}

	public static final class DeathriteShamanAbility1 extends ActivatedAbility
	{
		public DeathriteShamanAbility1(GameState state)
		{
			super(state, "(B), (T): Exile target instant or sorcery card from a graveyard. Each opponent loses 2 life.");
			this.setManaCost(new ManaPool("(B)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(Players.instance()))), "target instant or sorcery card in a graveyard"));
			this.addEffect(exile(target, "Exile target instant or sorcery card from a graveyard."));
			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 2, "Each opponent loses 2 life."));
		}
	}

	public static final class DeathriteShamanAbility2 extends ActivatedAbility
	{
		public DeathriteShamanAbility2(GameState state)
		{
			super(state, "(G), (T): Exile target creature card from a graveyard. You gain 2 life.");
			this.setManaCost(new ManaPool("(G)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))), "target creature card in a graveyard"));
			this.addEffect(exile(target, "Exile target creature card from a graveyard."));
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public DeathriteShaman(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// (T): Exile target land card from a graveyard. Add one mana of any
		// color to your mana pool.
		this.addAbility(new DeathriteShamanAbility0(state));

		// (B), (T): Exile target instant or sorcery card from a graveyard. Each
		// opponent loses 2 life.
		this.addAbility(new DeathriteShamanAbility1(state));

		// (G), (T): Exile target creature card from a graveyard. You gain 2
		// life.
		this.addAbility(new DeathriteShamanAbility2(state));
	}
}
