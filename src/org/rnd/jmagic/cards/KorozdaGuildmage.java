package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Korozda Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ELF})
@ManaCost("BG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class KorozdaGuildmage extends Card
{
	public static final class KorozdaGuildmageAbility0 extends ActivatedAbility
	{
		public KorozdaGuildmageAbility0(GameState state)
		{
			super(state, "(1)(B)(G): Target creature gets +1/+1 and gains intimidate until end of turn.");
			this.setManaCost(new ManaPool("(1)(B)(G)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 and gains intimidate until end of turn.", org.rnd.jmagic.abilities.keywords.Intimidate.class));
		}
	}

	public static final class KorozdaGuildmageAbility1 extends ActivatedAbility
	{
		public KorozdaGuildmageAbility1(GameState state)
		{
			super(state, "(2)(B)(G), Sacrifice a nontoken creature: Put X 1/1 green Saproling creature tokens onto the battlefield, where X is the sacrificed creature's toughness.");
			this.setManaCost(new ManaPool("(2)(B)(G)"));

			EventFactory sacrifice = sacrifice(You.instance(), 1, RelativeComplement.instance(CreaturePermanents.instance(), Tokens.instance()), "Sacrifice a nontoken creature");
			this.addCost(sacrifice);

			CreateTokensFactory factory = new CreateTokensFactory(ToughnessOf.instance(OldObjectOf.instance(CostResult.instance(sacrifice))), "Put X 1/1 green Saproling creature tokens onto the battlefield, where X is the sacrificed creature's toughness.");
			factory.addCreature(1, 1);
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.SAPROLING);
			this.addEffect(factory.getEventFactory());
		}
	}

	public KorozdaGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(B)(G): Target creature gets +1/+1 and gains intimidate until end
		// of turn.
		this.addAbility(new KorozdaGuildmageAbility0(state));

		// (2)(B)(G), Sacrifice a nontoken creature: Put X 1/1 green Saproling
		// creature tokens onto the battlefield, where X is the sacrificed
		// creature's toughness.
		this.addAbility(new KorozdaGuildmageAbility1(state));
	}
}
