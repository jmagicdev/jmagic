package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cavalry Master")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class CavalryMaster extends Card
{
	public static final class CavalryMasterAbility1 extends StaticAbility
	{
		public CavalryMasterAbility1(GameState state)
		{
			super(state, "Other creatures you control with flanking have flanking.");

			SetGenerator hasFlanking = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flanking.class);
			SetGenerator yourFlanking = Intersect.instance(CREATURES_YOU_CONTROL, hasFlanking);
			SetGenerator other = RelativeComplement.instance(yourFlanking, This.instance());
			this.addEffectPart(addAbilityToObject(other, org.rnd.jmagic.abilities.keywords.Flanking.class));
		}
	}

	public CavalryMaster(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flanking (Whenever a creature without flanking blocks this creature,
		// the blocking creature gets -1/-1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flanking(state));

		// Other creatures you control with flanking have flanking. (Each
		// instance of flanking triggers separately.)
		this.addAbility(new CavalryMasterAbility1(state));
	}
}
