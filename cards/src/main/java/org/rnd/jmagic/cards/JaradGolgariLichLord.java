package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jarad, Golgari Lich Lord")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.ZOMBIE})
@ManaCost("BBGG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class JaradGolgariLichLord extends Card
{
	public static final class JaradGolgariLichLordAbility0 extends StaticAbility
	{
		public JaradGolgariLichLordAbility0(GameState state)
		{
			super(state, "Jarad, Golgari Lich Lord gets +1/+1 for each creature card in your graveyard.");

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator creaturesInYourGraveyard = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yourGraveyard));
			SetGenerator N = Count.instance(creaturesInYourGraveyard);
			this.addEffectPart(modifyPowerAndToughness(This.instance(), N, N));
		}
	}

	public static final class JaradGolgariLichLordAbility1 extends ActivatedAbility
	{
		public JaradGolgariLichLordAbility1(GameState state)
		{
			super(state, "(1)(B)(G), Sacrifice another creature: Each opponent loses life equal to the sacrificed creature's power.");
			this.setManaCost(new ManaPool("(1)(B)(G)"));

			// Sacrifice another creature
			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			EventFactory sacrifice = sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature");
			this.addCost(sacrifice);

			SetGenerator thatCreature = OldObjectOf.instance(CostResult.instance(sacrifice));
			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), PowerOf.instance(thatCreature), "Each opponent loses life equal to the sacrificed creature's power."));
		}
	}

	public static final class JaradGolgariLichLordAbility2 extends ActivatedAbility
	{
		public JaradGolgariLichLordAbility2(GameState state)
		{
			super(state, "Sacrifice a Swamp and a Forest: Return Jarad from your graveyard to your hand.");
			// Sacrifice a Swamp and a Forest
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.SWAMP), "Sacrifice a Swamp"));
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.FOREST), "and a Forest"));

			this.addEffect(putIntoHand(ABILITY_SOURCE_OF_THIS, You.instance(), "Return Jarad from your graveyard to your hand."));

			this.activateOnlyFromGraveyard();
		}
	}

	public JaradGolgariLichLord(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Jarad, Golgari Lich Lord gets +1/+1 for each creature card in your
		// graveyard.
		this.addAbility(new JaradGolgariLichLordAbility0(state));

		// (1)(B)(G), Sacrifice another creature: Each opponent loses life equal
		// to the sacrificed creature's power.
		this.addAbility(new JaradGolgariLichLordAbility1(state));

		// Sacrifice a Swamp and a Forest: Return Jarad from your graveyard to
		// your hand.
		this.addAbility(new JaradGolgariLichLordAbility2(state));
	}
}
