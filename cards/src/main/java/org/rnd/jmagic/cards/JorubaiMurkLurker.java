package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jorubai Murk Lurker")
@Types({Type.CREATURE})
@SubTypes({SubType.LEECH})
@ManaCost("2U")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class JorubaiMurkLurker extends Card
{
	public static final class JorubaiMurkLurkerAbility0 extends StaticAbility
	{
		public JorubaiMurkLurkerAbility0(GameState state)
		{
			super(state, "Jorubai Murk Lurker gets +1/+1 as long as you control a Swamp.");

			// Wild Nacatl gets +1/+1
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			// as long as you control a Plains.
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator swamp = HasSubType.instance(SubType.SWAMP);
			SetGenerator youControlAPlains = Intersect.instance(youControl, swamp);
			this.canApply = Both.instance(youControlAPlains, this.canApply);
		}
	}

	public static final class JorubaiMurkLurkerAbility1 extends ActivatedAbility
	{
		public JorubaiMurkLurkerAbility1(GameState state)
		{
			super(state, "(1)(B): Target creature gains lifelink until end of turn.");
			this.setManaCost(new ManaPool("(1)(B)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Target creature gains lifelink until end of turn."));
		}
	}

	public JorubaiMurkLurker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Jorubai Murk Lurker gets +1/+1 as long as you control a Swamp.
		this.addAbility(new JorubaiMurkLurkerAbility0(state));

		// (1)(B): Target creature gains lifelink until end of turn. (Damage
		// dealt by the creature also causes its controller to gain that much
		// life.)
		this.addAbility(new JorubaiMurkLurkerAbility1(state));
	}
}
