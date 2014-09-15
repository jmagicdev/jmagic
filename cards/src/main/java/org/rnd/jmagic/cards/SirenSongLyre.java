package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Siren Song Lyre")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class SirenSongLyre extends Card
{
	public static final class TapThings extends ActivatedAbility
	{
		public TapThings(GameState state)
		{
			super(state, "(2), (T): Tap target creature.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));
		}
	}

	public static final class SirenSongLyreAbility0 extends StaticAbility
	{
		public SirenSongLyreAbility0(GameState state)
		{
			super(state, "Equipped creature has \"(2), (T): Tap target creature.\"");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), TapThings.class));
		}
	}

	public SirenSongLyre(GameState state)
	{
		super(state);

		// Equipped creature has "(2), (T): Tap target creature."
		this.addAbility(new SirenSongLyreAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
